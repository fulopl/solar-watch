import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Layout from "./Pages/Layout";
import ErrorPage from "./Pages/ErrorPage";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import MainPage from "./Pages/MainPage";
import RegistrationPage from "./Pages/RegistrationPage";
import SignInPage from "./Pages/SignInPage";
import SunriseSunsetTimesPage from "./Pages/SunriseSunsetTimesPage";
import Token from "./Pages/Token";
import UserEditorPage from "./Pages/Editor/UserEditorPage";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: "/",
                element: <MainPage />,
            },
            {
                path: "/sunrise-sunset-times",
                element: <SunriseSunsetTimesPage />,
            },
            {
                path: "/sign-in",
                element: <SignInPage />,
            },
            {
                path: "/register",
                element: <RegistrationPage />,
            },
            {
                path: "/token",
                element: <Token />,
            },
            {
                path: "/user-editor",
                element: <UserEditorPage />,
            },
        ]
    }
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
