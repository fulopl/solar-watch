import {useEffect, useState} from "react";
import Loading from "../Components/Loading";

const auth = (token) => {
    return fetch("api/user/auth",
        {
            method: "GET",
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
        }
    );
}

const MainPage = () => {
    const [isLoading, setLoading] = useState(true);
    const [isAuthOk, setAuthOk] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("jwt");
        auth(token)
            .then(res => {
                if (!res.ok) throw new Error(res.statusText);
                setAuthOk(true);
            })
            .catch(error => {
                console.log(error)
            })
            .finally(()=>{
                setLoading(false)
            })
    }, [])

    if (isLoading) {
        return <Loading/>;
    }

    return (
        <div className="container-main">
            <div className="textbox-main">
                <h1>Welcome to SolarWatch!</h1>
                {
                    !isAuthOk ?
                        <h2>Select 'Sign in' to log in with an existing user or register a new one!</h2>
                        : <h2>You are signed in with: {localStorage.getItem("userName")}</h2>
                }
            </div>
        </div>
    )
}

export default MainPage;