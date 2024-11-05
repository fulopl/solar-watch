const ServerMessagePage = ({message, onOk}) => {

    return (
        <div className="container-main">
            <div className="textbox-main">
                <h2>{message}</h2>
                <button type="button" onClick={onOk}>
                    OK
                </button>
            </div>
        </div>
    );
}

export default ServerMessagePage;