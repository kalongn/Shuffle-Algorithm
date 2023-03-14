#!/bin/bash

returnToken() {
    node authoToken.js
}

updatePlayList() {
    token=$1
    playlist_id=$2
    uris=$3

    URL="https://api.spotify.com/v1/playlists/"$playlist_id"/tracks?uris=$uris"

    curl --request PUT \
        --url $URL \
        --header 'Authorization: Bearer '$token \
        --header 'Content-Type: application/json' > "update$playlist_id.JSON" \
        --data '{
        }'
    #
}

#program begin here
filename="shuffleResultPlaylist.txt"
read -r playlist_id<$filename
firstLine=false
auth_token=$(returnToken)
while read line; do
    if $firstLine ; then
        $(updatePlayList $auth_token $playlist_id $line)
    fi
    firstLine=true
done < $filename

exit 0
