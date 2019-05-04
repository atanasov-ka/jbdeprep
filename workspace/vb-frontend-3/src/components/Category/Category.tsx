import React from 'react';
import Button from '@material-ui/core/Button';
import './Category.css';
import Card from '@material-ui/core/Card';
import SvgIcon from '@material-ui/core/SvgIcon';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';


const Category = ( {categoryName, boxId} ) => (
    <Card className={"card"}>
        <CardContent>
            <SvgIcon>
                <path d="M20 12l-1.41-1.41L13 16.17V4h-2v12.17l-5.58-5.59L4 12l8 8 8-8z" />
            </SvgIcon>
            <Typography className={"title"} variant="h5" component="h2">
                {categoryName}
            </Typography>
        </CardContent>
        <CardActions>
            <Button size="small">List Boxes</Button>
        </CardActions>
    </Card>
    );

export default Category;
