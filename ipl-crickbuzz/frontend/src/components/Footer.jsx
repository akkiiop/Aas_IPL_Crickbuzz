function Footer() {
    return (
        <footer className="footer">
            <div className="footer-container">

                <div className="footer-brand">
                    <span className="footer-icon">🏏</span>

                    <div>
                        <h3>IPL Crickbuzz</h3>
                        <p>
                            Cricket statistics, teams and player management.
                        </p>
                    </div>
                </div>

                <div className="footer-right">
                    <span>Spring Boot</span>
                    <span>React</span>
                    <span>MySQL</span>
                    <span>AWS</span>
                </div>

            </div>

            <div className="footer-bottom">
                © {new Date().getFullYear()} IPL Crickbuzz. All rights reserved.
            </div>
        </footer>
    );
}

export default Footer;