import SignInForm from "../Components/SignInForm";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";

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
                    alert(`Login success: ${res.userName}`);
                    navigate("/");
                    window.location.reload();
                }
                else if (res.error === "Unauthorized") {
                    alert(`Incorrect username or password. Please try again!`);
                }
                else {
                    alert(`An error occurred while processing your request. Please try again later!`);
                    navigate("/");
                }
            }
        );
    }
    return (
        <div>
            <h2>Sign in</h2>
            <SignInForm
                disabled={isLoading}
                onSave={handleSignIn}
                onCancel={() => navigate("/")}
            />
            <h2>...or create a new account</h2>
            <Link to="/register">
                <button type="button">Register</button>
            </Link>
        </div>
    )
}

export default SignInPage;