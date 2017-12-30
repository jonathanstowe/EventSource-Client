# EventSource::Client

A Perl 6 client for (Server-Sent Events)[https://www.w3.org/TR/eventsource/]

## Synopsis

```perl6
use EventSource::Client;

react {
    whenever EventSource::Client.new(source => "http://127.0.0.1:7798/") -> $event {
        say "> " ~ $event.data;
    }
}
```

## Description



## Installation

Assuming you have a working Rakudo Perl 6 installation then you should be able to
install this with `zef` :

    zef install EventSource::Client

    # Or from a local clone

    zef install .

## Support

# Copyright & Licence

This is free software, please see the [LICENCE](LICENCE) for details.

© Jonathan Stowe 2017
