import axios from "axios";

export default axios.create({
    baseURL: "YOUR_BACKEND_SERVER_URL",
    headers: {
        "Content-type": "application/json",
    },
});