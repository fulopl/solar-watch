const Token = () => {
    return <>
        <p>{localStorage.getItem("jwt")}</p>
        <p>{localStorage.getItem("userName")}</p>
        <p>{localStorage.getItem("roles")}</p>
    </>
}

export default Token;