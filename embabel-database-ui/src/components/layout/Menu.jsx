
import { useNavigate } from 'react-router-dom';
import { Button, ButtonGroup, EntityTitle, Section, SectionCard } from "@blueprintjs/core";
import { Database } from "@blueprintjs/icons";
import { version } from '../../../package.json';

function Menu() {

    const navigate = useNavigate();

    const handleGoSearch = () => {
        navigate('/search');
    }

    const handleGoTags = () => {
        navigate('/tags');
    }

    const handleGoHome = () => {
        navigate('/');
    }    

    return (
        <Section style={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
            <SectionCard>
                <EntityTitle title="Embabel LLM Database" icon={<Database/>}/>
            </SectionCard>
            <div style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
                <SectionCard>
                    <ButtonGroup vertical={true} fill={true}>
                        <Button onClick={handleGoHome}>Home</Button>
                        <Button onClick={handleGoSearch}>Search</Button>
                        <Button onClick={handleGoTags}>Tags</Button>
                        <Button>Providers</Button>
                    </ButtonGroup>
                </SectionCard>
            </div>
            <SectionCard style={{ position: 'sticky', bottom: 0, zIndex: 1, background: 'inherit' }}>
                v{version}
            </SectionCard>
        </Section>
    );
}

export default Menu