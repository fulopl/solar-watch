import SignInForm from "../Components/SignInForm";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import Loading from "../Components/Loading";

const signIn = (user) => {
    return fetch("api/user/signin",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        }
    ).then(res => res.json());
}

const SignInPage = ({setUserContext}) => {
    const navigate = useNavigate();
    const [isLoading, setLoading] = useState(false);

    const handleSignIn = (user) => {
        setLoading(true);
        signIn(user).then((res) => {
                setLoading(false);
                if (res.jwt) {
                    localStorage.setItem("jwt", res.jwt);
                    localStorage.setItem("userName", res.userName);
                    localStorage.setItem("roles", res.roles);
                    localStorage.setItem("enableReload", "true")
                    navigate("/sign-in-message");
                } else if (res.error === "Unauthorized") {
                    alert(`Incorrect username or password. Please try again!`);
                } else {
                    alert(`An error occurred while processing your request. Please try again later!`);
                    navigate("/");
                }
            }
        );
    }

    if (isLoading) {
        return <Loading/>;
    }

    return (
        <div className="container-main">
            <div className="textbox-main">
                <h2>Sign in</h2>
                <SignInForm
                    disabled={isLoading}
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

export default SignInPage;