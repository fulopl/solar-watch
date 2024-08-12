import SignInForm from "../Components/SignInForm";
import {useNavigate} from "react-router-dom";
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
                alert("User signed in.");
                localStorage.setItem("jwt", res.jwt);
                localStorage.setItem("userName", res.userName);
                localStorage.setItem("roles", res.roles);
                navigate("/");
                //window.location.reload();
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
        </div>
    )
}

export default SignInPage;