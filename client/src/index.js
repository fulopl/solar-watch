import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Layout from "./pages/Layout";
import ErrorPage from "./pages/ErrorPage";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import MainPage from "./pages/MainPage";
import RegistrationPage from "./pages/RegistrationPage";
import SignInPage from "./pages/SignInPage";
import SunriseSunsetTimesPage from "./pages/SunriseSunsetTimesPage";
import UserEditorPage from "./pages/EditorPages/UserEditorPage";
import SignOutPage from "./pages/SignOutPage";
import CityEditorPage from "./pages/EditorPages/CityEditorPage";
import TimeEditorPage from "./pages/EditorPages/TimeEditorPage";
import UserProvider from "./context/UserProvider";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                path: "/",
                element: <MainPage/>,
            },
            {
                path: "/sunrise-sunset-times",
                element: <SunriseSunsetTimesPage/>,
            },
            {
                path: "/sign-in",
                element: <SignInPage/>,
            },
            {
                path: "/sign-out",
                element: <SignOutPage/>,
            },
            {
                path: "/register",
                element: <RegistrationPage/>,
            },
            {
                path: "/user-editor",
                element: <UserEditorPage/>,
            },
            {
                path: "/city-editor",
                element: <CityEditorPage/>,
            },
            {
                path: "/time-editor",
                element: <TimeEditorPage/>,
            },
        ]
    }
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <UserProvider>
            <RouterProvider router={router}/>
        </UserProvider>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
