import React, {useState} from 'react'
import {Button, Link, TextField, Typography} from "@mui/material";
import axios from "axios";
import RegisterForm from "./RegisterForm";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import Dialog from "@mui/material/Dialog";
import {useUsernameStore} from "./ZustanStore";

const Login = (props) => {

    const btnstyle = {margin: '8px 0'}
    const [loginData, setLoginData] = useState({
        username: '',
        password: ''
    });
    const [isDialogOpen, setIsDialogOpen] = useState(false)
    const {username, setUsername} = useUsernameStore();

    const handleLogin = () => {
        axios.post("http://localhost:8080/user/login", loginData)
            .then(res => setUsername(res.data.username))
            .catch((e) => alert(e));
        props.loginForm(false);
    }

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setLoginData(prevState => ({...prevState, [name]: value}));
    }

    const openDialog = () => {
        setIsDialogOpen(true);
    };

    const closeDialog = () => {
        setIsDialogOpen(false);
    };

    return (
        <Dialog open={props.loginForm} onClose={props.loginForm}>
            <DialogTitle>Sign In</DialogTitle>
            <DialogContent>
                <TextField label='Username' name="username" onChange={handleInputChange}
                           placeholder='Enter username' type="text" variant="outlined" required/>
                <TextField label='Password' name="password" onChange={handleInputChange}
                           placeholder='Enter password' type='password' variant="outlined" required/>
                <Button type='submit' color='primary' variant="contained" style={btnstyle} fullWidth
                        onClick={handleLogin}>
                    Sign in
                </Button>
                <Button type='submit' color='secondary' variant="contained" style={btnstyle} fullWidth
                        onClick={props.loginForm}>
                    Close
                </Button>
                <Typography>
                    <Link href="#">
                        Forgot password ?
                    </Link>
                </Typography>
                <Typography> Do you have an account ?
                    <Button onClick={openDialog}>
                        Sign Up
                    </Button>
                    <RegisterForm isOpen={isDialogOpen} onClose={closeDialog}/>
                </Typography>
            </DialogContent>
        </Dialog>
    )
}

export default Login
