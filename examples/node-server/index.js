#!/usr/bin/env node

const express = require('express')
const SseStream = require('ssestream')

const app = express()

app.get('/', (req, res) => {
  console.log('new connection')
  res.set('Transfer-Encoding', 'chunked');

  const sseStream = new SseStream(req)
  sseStream.pipe(res)
  const pusher = setInterval(() => {
    sseStream.write({
      event: 'server-time',
      data: new Date().toTimeString()
    })
  }, 1000)

  res.set('Transfer-Encoding', 'chunked');
  res.on('close', () => {
    console.log('lost connection')
    clearInterval(pusher)
    sseStream.unpipe(res)
  })
})

app.listen(7798, (err) => {
  if (err) throw err
  console.log('server ready on http://localhost:7798/')
})
