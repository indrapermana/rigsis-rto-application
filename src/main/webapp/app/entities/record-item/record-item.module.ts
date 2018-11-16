import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    RecordItemComponent,
    RecordItemDetailComponent,
    RecordItemUpdateComponent,
    RecordItemDeletePopupComponent,
    RecordItemDeleteDialogComponent,
    recordItemRoute,
    recordItemPopupRoute
} from './';

const ENTITY_STATES = [...recordItemRoute, ...recordItemPopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecordItemComponent,
        RecordItemDetailComponent,
        RecordItemUpdateComponent,
        RecordItemDeleteDialogComponent,
        RecordItemDeletePopupComponent
    ],
    entryComponents: [RecordItemComponent, RecordItemUpdateComponent, RecordItemDeleteDialogComponent, RecordItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppRecordItemModule {}
