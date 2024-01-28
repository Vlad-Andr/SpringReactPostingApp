import React, { useState } from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography, Button } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const CustomAccordion = () => {
    const [expanded, setExpanded] = useState(false);

    return (
        <div>
            <Accordion expanded={expanded}>
                <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography>Accordion Title</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Typography>First option</Typography>
                    <Typography>Second option</Typography>
                    <Typography>Third option</Typography>
                </AccordionDetails>
            </Accordion>
            <Button onClick={() => setExpanded(!expanded)}>
                {expanded ? 'Collapse' : 'Expand'}
            </Button>
        </div>
    );
};

export default CustomAccordion;
