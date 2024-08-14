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
        <div className="Layout">
            <nav>
                <ul>
                    <li className="grow">
                        <Link to="/">Solar Watch</Link>
                    </li>
                    <li>
                        <Link to="/sunrise-sunset-times">Sunrise & Sunset Times</Link>
                    </li>
                    {(token !== "null") ?
                        <li>
                            <button type="button"
                                    onClick={() => {
                                        localStorage.setItem("jwt", null);
                                        localStorage.setItem("userName", null);
                                        localStorage.setItem("roles", null);
                                        window.location.reload();
                                    }}
                            >Sign Out
                            </button>
                        </li>
                        :
                        <li>
                            <Link to="/sign-in">
                                <button type="button">Sign In</button>
                            </Link>
                        </li>
                    }
                    <li>
                        <Link to="/register">
                            <button type="button">Register</button>
                        </Link>
                    </li>
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
