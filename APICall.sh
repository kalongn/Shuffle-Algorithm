#!/bin/bash

Token=$(node fetchToken.js)

typeOfAnalysis='tracks'
URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$1

curl --request GET \
  --url $URL \
  --header 'Authorization: Bearer '$Token \
  --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
#
nameOfTrack=$(jq .name SongTempDatas/Song$typeOfAnalysis.JSON)

#Create the Song file
echo "" > SongDatas/"$nameOfTrack".txt

#Add the Track name into the Song file
echo 'Track Name: '$nameOfTrack >> "SongDatas/$nameOfTrack.txt"

#Add the Track ID into the Song file
echo 'Track ID: '$(jq .id SongTempDatas/Song$typeOfAnalysis.JSON) >> "SongDatas/$nameOfTrack.txt"

#Add all the artists name, ID into this Song file.
artistsLength=$(jq '.artists | length' SongTempDatas/Song$typeOfAnalysis.JSON)
for (( i=1 ; i<=$artistsLength; i++ ))
do
  indexArr=$i-1
  echo 'Artist '$i' Name: '$(jq .artists[$indexArr].name SongTempDatas/Song$typeOfAnalysis.JSON) >> "SongDatas/$nameOfTrack.txt"
  echo 'Artist '$i' Id: '$(jq .artists[$indexArr].id SongTempDatas/Song$typeOfAnalysis.JSON) >> "SongDatas/$nameOfTrack.txt"
done

#Add the tracks popularity into the Song file.
echo 'Track popularity: '$(jq .popularity SongTempDatas/Song$typeOfAnalysis.JSON) >> "SongDatas/$nameOfTrack.txt"

#Need To Start working from this part.
#typeOfAnalysis='albums'
#URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2
#curl --request GET \
#  --url $URL \
#  --header 'Authorization: Bearer '$Token \
#  --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
#

#typeOfAnalysis='artists'
#URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2
#curl --request GET \
#  --url $URL \
#  --header 'Authorization: Bearer '$Token \
#  --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
#

exit 0

