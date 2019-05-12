import React from 'react';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Button from "@material-ui/core/Button/Button";
import FolderIcon from "@material-ui/icons/Folder";

import './Category.css';
import Fade from "@material-ui/core/Fade/Fade";

const Category = ( {groupId, groupName, boxCount} ) => (
    <Fade in={true}>
        <Card className={"card"}>
            <CardContent>
                <FolderIcon />
                <Typography className={"title"} variant="h5" component="h2">
                    <span>{groupName}</span>
                </Typography>
                <Typography className={"smaller"} component="h2">Contains {boxCount} boxes</Typography>
            </CardContent>
            <CardActions>
                <Button href={"/category/" + groupId}>Open</Button>
            </CardActions>
        </Card>
    </Fade>
);

export default Category;
