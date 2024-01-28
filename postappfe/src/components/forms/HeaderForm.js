import * as React from 'react';
import {useState} from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import {useUsernameStore} from "./ZustanStore";
import {AppBar, Box, Button, ButtonGroup, IconButton, Toolbar, Typography} from "@mui/material";
import LoginForm from "./LoginForm";

export default function HeaderForm() {
    const [expanded, setExpanded] = useState(false);
    const [showLogin, setShowLogin] = useState(false)
    const {username, setUsername} = useUsernameStore();

    const buttons = [
        <Button style={{color : "white"}} onClick={() => alert("1")}>One</Button>,
        <Button style={{color : "white"}} onClick={() => alert("2")}>Two</Button>,
        <Button style={{color : "white"}} onClick={() => alert("3")}>Three</Button>
    ]

    console.log(username)

    const loginForm = () => {
        setShowLogin(!showLogin)
    }

    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static" style={{backgroundColor: "mediumpurple"}}>
                <Toolbar>
                    <IconButton
                        size="small"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        onClick={() => setExpanded(!expanded)}
                    >
                        <MenuIcon/>
                        {expanded &&
                            <ButtonGroup size="large" aria-label="large button group" orientation={"vertical"}>
                                {buttons}
                            </ButtonGroup>
                        }
                    </IconButton>
                    <Typography color="white" variant="h5" component="div" sx={{flexGrow: 1}}>
                        News
                    </Typography>
                    <Button color="inherit" onClick={loginForm}>{!showLogin && username}</Button>
                    {showLogin && <LoginForm loginForm={loginForm}/>}
                </Toolbar>
            </AppBar>
        </Box>
    );
}
