import {Link, Outlet} from "react-router-dom";

//import "./Layout.css";

const Layout = () => (
    <div className="Layout">
        <nav>
            <ul>
                <li className="grow">
                    <Link to="/">Solar Watch</Link>
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
            </ul>
        </nav>
        <Outlet/>
    </div>
);

export default Layout;
