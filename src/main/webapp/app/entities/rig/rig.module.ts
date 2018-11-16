import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    RigComponent,
    RigDetailComponent,
    RigUpdateComponent,
    RigDeletePopupComponent,
    RigDeleteDialogComponent,
    rigRoute,
    rigPopupRoute
} from './';

const ENTITY_STATES = [...rigRoute, ...rigPopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RigComponent, RigDetailComponent, RigUpdateComponent, RigDeleteDialogComponent, RigDeletePopupComponent],
    entryComponents: [RigComponent, RigUpdateComponent, RigDeleteDialogComponent, RigDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppRigModule {}
