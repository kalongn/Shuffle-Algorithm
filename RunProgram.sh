#!/bin/bash

getPlayListIDFromURL() {
  #remove everything minus the 32 characters playlist ID.
  echo "${1:34:-20}"
}

playlist_id=$(getPlayListIDFromURL $1)
bash "./APICall.sh" $playlist_id