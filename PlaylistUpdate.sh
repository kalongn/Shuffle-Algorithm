#!/bin/bash

returnToken() {
    node fetchToken.js
}

#program begin here
filename="shuffleResultPlaylist.txt"
read -r playlist_id<$filename
firstLine=false
while read line; do
    if $firstLine ; then
        echo $line
    fi
    firstLine=true
done < $filename

exit 0
