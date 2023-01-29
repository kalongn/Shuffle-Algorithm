#!/bin/bash

Token=$(node fetchToken.js)

case $1 in 

  #tracks + audio-analysis
  'tk')
    typeOfAnalysis='tracks'
    URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2

    curl --request GET \
      --url $URL \
      --header 'Authorization: Bearer '$Token \
      --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
    #
    nameOfTrack=$(jq .name SongTempDatas/Song$typeOfAnalysis.JSON)
    touch SongDatas/"$nameOfTrack".txt
    echo 'Track Name: '$nameOfTrack >> "SongDatas/$nameOfTrack.txt"


    typeOfAnalysis='audio-analysis'
    URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2
    curl --request GET \
      --url $URL \
      --header 'Authorization: Bearer '$Token \
      --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
    #
    exit 0
  ;;

  #artists
  'at')
    typeOfAnalysis='artists'
  ;;

  #albums
  'ab')
    typeOfAnalysis='albums'
  ;;

  #playlists
  'pl')
    typeOfAnalysis='playlists'
  ;;

  *)
    echo "Not a Valid Input for API call (arguemnt 1)."
    exit 1
  ;;
esac

exit 0