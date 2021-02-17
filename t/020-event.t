#!/usr/bin/env raku

use Test;
use EventSource::Client;
use EventSource::Server;

use Cro::HTTP::Router;
use Cro::HTTP::Server;

use Test::Util::ServerPort;

my $port = get-unused-port();


my $supply = ( EventSource::Server::Event.new(type => 'test', data => "test data") ).Supply;

my $es = EventSource::Server.new(:$supply);

my $app = route {
    get -> {
        content 'text/event-stream', $es.out-supply;
    }
};

my Cro::Service $tick = Cro::HTTP::Server.new(:host<127.0.0.1>, port => $port, application => $app);

$tick.start;



react {
    whenever EventSource::Client.new(source => "http://127.0.0.1:$port/") -> $event {
        isa-ok $event, EventSource::Client::MessageEvent;
        is $event.type, 'test', "got the expected type";
        is $event.data, 'test data', "got the expected data";
        done;
    }
}

$tick.stop;

done-testing();
# vim: ft=raku
