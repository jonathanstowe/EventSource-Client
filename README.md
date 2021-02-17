# EventSource::Client

A Raku client for [Server-Sent Events](https://www.w3.org/TR/eventsource/)

![Build Status](https://github.com/jonathanstowe/EventSource-Client/workflows/CI/badge.svg)

## Synopsis

```raku
use EventSource::Client;

react {
    whenever EventSource::Client.new(source => "http://127.0.0.1:7798/") -> $event {
        say "> " ~ $event.data;
    }
}
```

## Description

[Server-Sent Events](https://www.w3.org/TR/eventsource/) provide a mechanism for
timely delivery of server to client events over HTTP and can in some use-cases
be more convenient than a similar pattern implemented using Web Sockets and is
easier on the network than, for instance, HTTP polling by the client.

This library allows you to consume server sent events in a Raku program in
a reasonably idiomatic and reactive fashion.

## Installation

Assuming you have a working Rakudo installation then you should be able to
install this with `zef` :

    zef install EventSource::Client

    # Or from a local clone

    zef install .

## Support

Please post any bugs, feature requests or patches at  [github](https://github.com/jonathanstowe/EventSource-Client/issues)

## Copyright & Licence

This is free software, please see the [LICENCE](LICENCE) for details.

© Jonathan Stowe 2020-2021
