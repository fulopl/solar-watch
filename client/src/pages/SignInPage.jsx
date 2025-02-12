import SignInForm from "../components/SignInForm";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import {useUser} from "../context/UserProvider";

const SignInPage = () => {
    const navigate = useNavigate();
    const [isDisabled,setDisabled] = useState(false);
    const {message, reSetMessage, login} = useUser();

    const handleSignIn = (userCredentials) => {
        setDisabled(true);
        login(userCredentials);
    }

    if (!message) {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>Sign in</h2>
                    <SignInForm
                        disabled={isDisabled}
                        onSave={handleSignIn}
                    />
                    <h2>...or create a new account</h2>
                    <Link to="/register">
                        <button type="button">Register</button>
                    </Link>
                </div>
            </div>
        )
    }

    if (message === "OK") {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>You have successfully signed in.</h2>
                    <button type="button" onClick={() => navigate("/")}>
                        Go to main page!
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="container-main">
            <div className="textbox-main">
                <h2>{message}</h2>
                <button type="button" onClick={() => {
                    reSetMessage();
                    setDisabled(false);
                }}>
                    OK
                </button>
            </div>
        </div>
    );
}

export default SignInPage;