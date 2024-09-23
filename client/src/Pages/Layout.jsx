import {Link, Outlet} from "react-router-dom";
import {useEffect, useState} from "react";
import {logDOM} from "@testing-library/react";

//import "./Layout.css";

const fetchUserContext = (token) => {
    return fetch("api/user/context",
        {
            method: "GET",
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
        }
    ).then(res => res.json());
}

const Layout = () => {
    // const [userName, setUserName] = useState("");
    // const [authorities, setAuthorities] = useState("");
    const [token, setToken] = useState();

    useEffect(() => {
        setToken(localStorage.getItem("jwt"));
    }, [])

    return (
        <div className="main">
            <nav className="navbar">
                <ul>
                    <li className="grow">
                        <Link to="/">Main page</Link>
                    </li>
                    <li>
                        <Link to="/sunrise-sunset-times">Sunrise & Sunset Times</Link>
                    </li>
                    {(token !== "null") ?
                        <li>
                            <Link to="/sign-out">Sign out</Link>
                        </li>
                        :
                        <li>
                            <Link to="/sign-in">Sign in</Link>
                        </li>
                    }
                    <li>
                        <Link to="/token">Token</Link>
                    </li>
                    {
                        localStorage.getItem("roles")?.includes("ROLE_ADMIN") ?
                            <li>
                                <Link to="/user-editor">Edit users</Link>
                            </li>
                            : <></>
                    }
                </ul>
            </nav>
            <Outlet/>
        </div>
    )
}


export default Layout;
