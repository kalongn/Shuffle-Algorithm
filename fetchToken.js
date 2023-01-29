#!/usr/bin/env node
require("dotenv").config();

const axios = require("axios");
const qs = require("qs");

const getToken = async (
    clientId = process.env.CLIENT_ID,
    clientSecret = process.env.CLIENT_SECRET
) => {
    const headers = {
        headers: {
            Accept: "application/json",
            "Content-Type": "application/x-www-form-urlencoded",
        },
        auth: {
            username: clientId,
            password: clientSecret,
        },
    };
    const data = {
        grant_type: "client_credentials",
    };
    try {
        const response = await axios.post(
            "https://accounts.spotify.com/api/token",
            qs.stringify(data),
            headers
        );
        return response.data.access_token;
    } catch (err) {
        console.log(err);
    }
};

getToken().then(function (result) {
    console.log(result);
});