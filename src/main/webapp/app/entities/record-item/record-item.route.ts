import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';
import { RecordItemComponent } from './record-item.component';
import { RecordItemDetailComponent } from './record-item-detail.component';
import { RecordItemUpdateComponent } from './record-item-update.component';
import { RecordItemDeletePopupComponent } from './record-item-delete-dialog.component';
import { IRecordItem } from 'app/shared/model/record-item.model';

@Injectable({ providedIn: 'root' })
export class RecordItemResolve implements Resolve<IRecordItem> {
    constructor(private service: RecordItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((recordItem: HttpResponse<RecordItem>) => recordItem.body));
        }
        return of(new RecordItem());
    }
}

export const recordItemRoute: Routes = [
    {
        path: 'record-item',
        component: RecordItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-item/:id/view',
        component: RecordItemDetailComponent,
        resolve: {
            recordItem: RecordItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-item/new',
        component: RecordItemUpdateComponent,
        resolve: {
            recordItem: RecordItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-item/:id/edit',
        component: RecordItemUpdateComponent,
        resolve: {
            recordItem: RecordItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recordItemPopupRoute: Routes = [
    {
        path: 'record-item/:id/delete',
        component: RecordItemDeletePopupComponent,
        resolve: {
            recordItem: RecordItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
