#!/bin/bash

URL='https://api.spotify.com/v1/audio-analysis/'$1
i=1

Token=$(node fetchToken.js)

curl --request GET \
  --url $URL \
  --header 'Authorization: Bearer '$Token \
  --header 'Content-Type: application/json' > SongDatas/Song$i.JSON