import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

export default function SignInMessagePage() {
    const navigate = useNavigate();
    const [isLoaded, setLoaded] = useState(false);

    if (localStorage.getItem("enableReload") === "true") {
        localStorage.setItem("enableReload", "false");
        window.location.reload();
    }

    return (
        <div className="container-main">
            <div className="textbox-main">
                <h2>You have successfully signed in.</h2>
                <button type="button" onClick={() => navigate("/")}>
                    Go to main page!
                </button>
            </div>
        </div>
    )
}