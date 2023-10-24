#!/usr/bin/env node
const axios = require('axios');
const fs = require('fs');
const util = require('util');
const exec = util.promisify(require('child_process').exec);

async function returnToken() {
    try {
        const { stdout } = await exec('node fetchCLIToken.js');
        return stdout.trim();
    } catch (error) {
        console.error('Error executing fetchCLIToken.js:', error);
        return null;
    }
}

async function getPlaylistJSON(token, playlistId) {
    const URL = `https://api.spotify.com/v1/playlists/${playlistId}`;
    const headers = {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
    };

    try {
        const response = await axios.get(URL, { headers });
        fs.writeFileSync(`API_Datas/PlaylistTempDatas/${playlistId}.JSON`, JSON.stringify(response.data));

        const playlistName = response.data.name;
        fs.writeFileSync(`Condensed_Datas/PlaylistDatas/${playlistId}.txt`, playlistName + '\n');

        const playlistLength = response.data.tracks.items.length;
        for (let i = 0; i < playlistLength; i++) {
            const songId = response.data.tracks.items[i].track.id;
            await getTrackJSON(token, songId);
            fs.appendFileSync(`Condensed_Datas/PlaylistDatas/${playlistId}.txt`, songId + '\n');
        }
        fs.unlinkSync(`API_Datas/PlaylistTempDatas/${playlistId}.JSON`);
    } catch (error) {
        console.error('Error getting playlist data:', error);
    }
}

async function getTrackJSON(token, trackId) {
    const URL = `https://api.spotify.com/v1/tracks/${trackId}`;
    const headers = {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
    };

    try {
        const response = await axios.get(URL, { headers });
        fs.writeFileSync(`API_Datas/SongTempDatas/${trackId}.JSON`, JSON.stringify(response.data));
        await getCondensedDataFromTrack(token, `API_Datas/SongTempDatas/${trackId}.JSON`, trackId);
        fs.unlinkSync(`API_Datas/SongTempDatas/${trackId}.JSON`);
    } catch (error) {
        console.error('Error getting track data:', error);
    }
}

async function getCondensedDataFromTrack(token, filePath, trackId) {
    try {
        const data = JSON.parse(fs.readFileSync(filePath, 'utf8'));
        const trackName = data.name;
        const trackPopularity = data.popularity;

        let artistsName = '';
        let genres = '';

        for (const artist of data.artists) {
            const artistNames = artist.name;
            artistsName += artistNames + ',';
            const artistGenres = await getApproximateGenres(token, artist.id);
            genres += artistGenres;
        }

        artistsName = artistsName.slice(0, -1);

        const trackMiscInfo = await getTrackMISC(token, trackId);

        fs.writeFileSync(`Condensed_Datas/SongDatas/${trackId}.txt`, trackName + '\n');
        fs.appendFileSync(`Condensed_Datas/SongDatas/${trackId}.txt`, artistsName + '\n');
        fs.appendFileSync(`Condensed_Datas/SongDatas/${trackId}.txt`, trackPopularity + '\n');
        fs.appendFileSync(`Condensed_Datas/SongDatas/${trackId}.txt`, trackMiscInfo + '\n');
        if (genres.length == 0) {
            genres = 'No Genres Avaliable';
        }
        fs.appendFileSync(`Condensed_Datas/SongDatas/${trackId}.txt`, genres);
    } catch (error) {
        console.error('Error processing track data:', error);
    }
}

async function getApproximateGenres(token, artistId) {
    const URL = `https://api.spotify.com/v1/artists/${artistId}`;
    const headers = {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
    };

    try {
        const response = await axios.get(URL, { headers });
        const genres = response.data.genres;
        return genres.join(',');
    } catch (error) {
        console.error('Error getting artist data:', error);
        return '';
    }
}

async function getTrackMISC(token, trackId) {
    const URL = `https://api.spotify.com/v1/audio-features/${trackId}`;
    const headers = {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
    };

    try {
        const response = await axios.get(URL, { headers });
        const trackTempo = response.data.tempo;
        const trackValence = response.data.valence;
        const trackEnergy = response.data.energy;
        const trackDanceability = response.data.danceability;
        const trackAcousticness = response.data.acousticness;

        return `${trackTempo}\n${trackValence}\n${trackEnergy}\n${trackDanceability}\n${trackAcousticness}`;
    } catch (error) {
        console.error('Error getting track MISC data:', error);
        return '';
    }
}


async function main() {
    const token = await returnToken();
    getPlaylistJSON(token, playlistId);
}

// Define playlistId here.
let playlistId = null;
if (process.argv[2] !== null) {
    playlistId = process.argv[2];
} else {
    return -1;
}

main();