import React from "react";
import { Container, Grid, Typography } from "@mui/material";

export const FooterForm = () => {
    const containerStyle = {
        display: "flex",
        flexDirection: "column",
        minHeight: "100vh",
    };

    const footerStyle = {
        flexShrink: 0,
        backgroundColor: "rgb(65, 69, 75)",
        padding: "1rem",
        marginTop: "auto", // Push the footer to the bottom
    };

    return (
        <div style={containerStyle}>
            <footer style={footerStyle}>
                <Container maxWidth="lg">
                    <Grid container direction="column" alignItems="center">
                        <Grid item xs={12}>
                            <Typography color="white" variant="h5">
                                Footer placement
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography color="textSecondary" variant="subtitle1">
                                {`${new Date().getFullYear()} | React | Material UI`}
                            </Typography>
                        </Grid>
                    </Grid>
                </Container>
            </footer>
        </div>
    );
};

export default FooterForm;
