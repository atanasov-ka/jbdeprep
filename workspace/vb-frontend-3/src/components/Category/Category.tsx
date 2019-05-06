import React from 'react';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import SvgIcon from '@material-ui/core/SvgIcon';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';

import './Category.css';

const Category = ( {groupId, groupName, boxCount} ) => (
    <Card className={"card"}>
        <CardContent>
            <Typography className={"title"} variant="h5" component="h2">
                <SvgIcon>
                    <path d="M18.208,2.958H8.875V1.792c0-0.644-0.522-1.167-1.167-1.167H1.875c-0.644,0-1.167,0.522-1.167,1.167v16.333
								c0,0.645,0.522,1.166,1.167,1.166h16.333c0.645,0,1.167-0.521,1.167-1.166v-14C19.375,3.48,18.853,2.958,18.208,2.958z
								 M18.208,17.542c0,0.322-0.261,0.583-0.583,0.583H2.458c-0.323,0-0.583-0.261-0.583-0.583V6.458h16.333V17.542z M17.625,5.292
								H1.875V2.375c0-0.323,0.261-0.583,0.583-0.583h4.667c0.323,0,0.583,0.261,0.583,0.583v1.75h9.917c0.322,0,0.583,0.261,0.583,0.583
								C18.208,5.031,17.947,5.292,17.625,5.292z" />
                </SvgIcon>
                <span>{groupName}</span>
            </Typography>
            <Typography className={"smaller"} component="h2">
                Contains {boxCount} boxes
            </Typography>
        </CardContent>
        <CardActions>
            <Button size="small">List Boxes</Button>
        </CardActions>
    </Card>
    );

export default Category;
