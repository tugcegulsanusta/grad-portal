import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/school">
        School
      </MenuItem>
      <MenuItem icon="asterisk" to="/graduation">
        Graduation
      </MenuItem>
      <MenuItem icon="asterisk" to="/grad">
        Grad
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-history">
        Job History
      </MenuItem>
      <MenuItem icon="asterisk" to="/skill">
        Skill
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
