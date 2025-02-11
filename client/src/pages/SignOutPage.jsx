import {useNavigate} from "react-router-dom";
import {useUser} from "../context/UserProvider";

export default function SignOutPage() {
    const navigate = useNavigate();
    const {user, logout} = useUser();

    logout();
    console.log("Logout page. User= " + JSON.stringify(user))

    // if (localStorage.getItem("jwt") !== "null") {
    //     localStorage.setItem("jwt", null);
    //     localStorage.setItem("userName", null);
    //     localStorage.setItem("roles", null);
    //     window.location.reload();
    // }

    return <>
        <div className="container-main">
            <div className="textbox-main">
                <h2>You have been signed out.</h2>
                <button type="button" onClick={() => navigate("/")}>
                    Go to main page!
                </button>
            </div>
        </div>
    </>
}