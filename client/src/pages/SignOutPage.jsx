import {useNavigate} from "react-router-dom";
import {useUser} from "../context/UserProvider";

export default function SignOutPage() {
    const navigate = useNavigate();
    const {reSetMessage, logout} = useUser();

    logout();
    reSetMessage();

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