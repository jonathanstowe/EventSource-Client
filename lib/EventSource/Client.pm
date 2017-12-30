use Cro::HTTP::Client;

class EventSource::Client {

    class MessageEvent {
      has Str $.type is default('message');
      has Str $.data;
      has Str $.lastEventId;
      has Str $.origin;
    }

    has Cro::HTTP::Client $.client;

    method client(--> Cro::HTTP::Client) {
        $!client //= Cro::HTTP::Client.new( headers => [ accept => "text/event-stream"]);
    }

    has Str $.source is required;

    method input-lines( --> Supply ) {
        self.client.get($!source).result.body-byte-stream.map( -> $v { $v.decode }).lines;
    }

    method Supply( --> Supply ) {
        supply {
            my Str $data    = '';
            my Str $event   = '';
            my Str $id      = '';

            whenever self.input-lines -> $line {
                if $line {
                    if $line !~~ /^':'/ {
                        my ( $field, $value ) = $line.split(/':'\s?/, 2);
                        given $field {
                            when 'event' {
                                $event = $value;
                            }
                            when 'id' {
                                $id = $value;
                            }
                            when 'data' {
                                if $data {
                                    $data ~= "\n";
                                }
                                $data ~= $value;
                            }
                        }
                    }
                }
                else {
                    if $data {
                        emit MessageEvent.new(:$data, type => $event || 'message', lastEventId => $id, origin => $!source);
                    }
                    $event = '';
                    $data  = '';
                }
            }
        }
    }
}

# vim: expandtab shiftwidth=4 ft=perl6
