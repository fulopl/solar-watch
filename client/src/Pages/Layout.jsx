import {Link, Outlet} from "react-router-dom";
import {useEffect, useState} from "react";

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
    const [isLoading, setLoading] = useState(true);
    // const [userName, setUserName] = useState("");
    // const [authorities, setAuthorities] = useState("");


    // useEffect(() => {
    //     const token = localStorage.getItem("jwt");
    //     fetchUserContext(token).then(resp => {
    //         console.log(resp)
    //         setAuthorities(resp.authorities.map(obj => obj.authority));
    //         setLoading(false)
    //     })
    // }, [localStorage.getItem("jwt")])

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
                    <li>
                        <Link to="/sign-in">
                            <button type="button">Sign In</button>
                        </Link>
                    </li>
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
