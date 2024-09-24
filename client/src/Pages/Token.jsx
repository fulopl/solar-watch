const Token = () => {
    return <>
        <div className="container-main">
            <div className="textbox-main">
                <p>{localStorage.getItem("jwt")}</p>
                <p>{localStorage.getItem("userName")}</p>
                <p>{localStorage.getItem("roles")}</p>
            </div>
        </div>

    </>
}

export default Token;