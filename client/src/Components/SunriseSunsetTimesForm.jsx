import {useState} from "react";

const SunriseSunsetTimesForm = ({onSave}) => {
    const [date, setDate] = useState("");
    const [city, setCity] = useState("");

//TODO fetch exsisting cities

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave(date, city);
    }

    return <>
        <form onSubmit={onSubmit}>
            <h3>Please enter date and city!</h3>
            <div>
                <label htmlFor="date">Date:</label>
                <input type="text" id="date" name="date"
                       onChange={(e) => setDate(e.target.value)}
                       placeholder="yyyy-mm-dd"
                       value={date}
                />
            </div>

            <div>
                <label htmlFor="city">City:</label>
                <input type="text" id="city" name="city"
                       onChange={(e) => setCity(e.target.value)}
                       placeholder="name of city"
                       value={city}
                />
            </div>

            <div>
                <button type="submit">
                    Go!
                </button>
            </div>
        </form>
    </>
};

export default SunriseSunsetTimesForm;