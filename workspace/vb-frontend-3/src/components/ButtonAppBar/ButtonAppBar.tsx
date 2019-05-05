import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Menu from '@material-ui/core/Menu';
import { withStyles } from '@material-ui/core/styles';
import Button from "@material-ui/core/Button/Button";

const styles = {
    root: {
        flexGrow: 1,
    },
    grow: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: 20,
    },
};

class ButtonAppBar extends React.Component {
    state =  {
        auth: true,
        anchorEl: null
    };

    logout = () => {
        console.info("clearing local storage" + localStorage.getItem("authToken2"));
        localStorage.clear();
        window.location.reload();
    };

    handleChange = event => {
        this.setState({ auth: event.target.checked });
    };

    handleMenu = event => {
        this.setState({ anchorEl: event.currentTarget });
    };

    handleClose = () => {
        this.setState({ anchorEl: null });
    };

    render() {
        const { anchorEl } = this.state;
        const open = Boolean(anchorEl);

        return (
            <div className={"root"}>
                <AppBar position="static">
                    <Toolbar>
                        <IconButton className={"menuButton"}
                                    color="inherit"
                                    aria-label="Menu"
                                    aria-owns={open ? 'menu-appbar2' : undefined}
                                    aria-haspopup="true"
                                    onClick={this.handleMenu}>
                            <MenuIcon />
                        </IconButton>
                        <Menu id="menu-appbar2"
                              anchorEl={anchorEl}
                              anchorOrigin={{
                                vertical: 'top',
                                horizontal: 'right'
                              }}
                              transformOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                              }}
                              open={open}
                              onClose={this.handleClose}>

                        </Menu>
                        <Typography variant="h6" color="inherit" className={"grow"}>
                            Verbbox App
                        </Typography>
                        <Button onClick={this.logout}>Logout</Button>
                    </Toolbar>
                </AppBar>
            </div>
        );
    }
}

export default withStyles(styles)(ButtonAppBar);