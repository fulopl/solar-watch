import {useNavigate} from "react-router-dom";

export default function SignOutPage() {
    const navigate = useNavigate()

    if (localStorage.getItem("jwt") !== "null") {
        localStorage.setItem("jwt", null);
        localStorage.setItem("userName", null);
        localStorage.setItem("roles", null);
        window.location.reload();
    }

    return <>
        <div className="container-main">
            <div className="textbox-main">
                <h2>You have been signed out.</h2>
                <button type="button" onClick={() => navigate("/")}>
                    Back to main page!
                </button>
            </div>
        </div>
    </>
}