import {Link, Outlet} from "react-router-dom";
import {useUser} from "../context/UserProvider";

const Layout = () => {
    const {user} = useUser();

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
