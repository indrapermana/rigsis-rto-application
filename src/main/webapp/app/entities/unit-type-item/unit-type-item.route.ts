import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnitTypeItem } from 'app/shared/model/unit-type-item.model';
import { UnitTypeItemService } from './unit-type-item.service';
import { UnitTypeItemComponent } from './unit-type-item.component';
import { UnitTypeItemDetailComponent } from './unit-type-item-detail.component';
import { UnitTypeItemUpdateComponent } from './unit-type-item-update.component';
import { UnitTypeItemDeletePopupComponent } from './unit-type-item-delete-dialog.component';
import { IUnitTypeItem } from 'app/shared/model/unit-type-item.model';

@Injectable({ providedIn: 'root' })
export class UnitTypeItemResolve implements Resolve<IUnitTypeItem> {
    constructor(private service: UnitTypeItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unitTypeItem: HttpResponse<UnitTypeItem>) => unitTypeItem.body));
        }
        return of(new UnitTypeItem());
    }
}

export const unitTypeItemRoute: Routes = [
    {
        path: 'unit-type-item',
        component: UnitTypeItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypeItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-type-item/:id/view',
        component: UnitTypeItemDetailComponent,
        resolve: {
            unitTypeItem: UnitTypeItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypeItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-type-item/new',
        component: UnitTypeItemUpdateComponent,
        resolve: {
            unitTypeItem: UnitTypeItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypeItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-type-item/:id/edit',
        component: UnitTypeItemUpdateComponent,
        resolve: {
            unitTypeItem: UnitTypeItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypeItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unitTypeItemPopupRoute: Routes = [
    {
        path: 'unit-type-item/:id/delete',
        component: UnitTypeItemDeletePopupComponent,
        resolve: {
            unitTypeItem: UnitTypeItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypeItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
