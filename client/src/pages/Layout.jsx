import {Link, Outlet} from "react-router-dom";
import {useEffect, useState} from "react";
import {useUser} from "../context/UserProvider";

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
    const [token, setToken] = useState();
    const {user} = useUser();

    console.log("layout. user=" + JSON.stringify(user))

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
                    {
                        user?.roles.includes("ROLE_ADMIN") ?
                            <li>
                                <Link to="/user-editor">Edit users</Link>
                            </li>
                            : <></>
                    }
                    {
                        user?.roles.includes("ROLE_ADMIN") ?
                            <li>
                                <Link to="/city-editor">Edit cities</Link>
                            </li>
                            : <></>
                    }
                    {
                        user?.roles.includes("ROLE_ADMIN") ?
                            <li>
                                <Link to="/time-editor">Edit times</Link>
                            </li>
                            : <></>
                    }
                    {(user !== null) ?
                        <li>
                            <Link to="/sign-out">Sign out</Link>
                        </li>
                        :
                        <li>
                            <Link to="/sign-in">Sign in / Register</Link>
                        </li>
                    }
                </ul>
            </nav>
            <Outlet/>
        </div>
    )
}


export default Layout;
