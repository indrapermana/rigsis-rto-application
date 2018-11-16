import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from './unit-type.service';
import { UnitTypeComponent } from './unit-type.component';
import { UnitTypeDetailComponent } from './unit-type-detail.component';
import { UnitTypeUpdateComponent } from './unit-type-update.component';
import { UnitTypeDeletePopupComponent } from './unit-type-delete-dialog.component';
import { IUnitType } from 'app/shared/model/unit-type.model';

@Injectable({ providedIn: 'root' })
export class UnitTypeResolve implements Resolve<IUnitType> {
    constructor(private service: UnitTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unitType: HttpResponse<UnitType>) => unitType.body));
        }
        return of(new UnitType());
    }
}

export const unitTypeRoute: Routes = [
    {
        path: 'unit-type',
        component: UnitTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-type/:id/view',
        component: UnitTypeDetailComponent,
        resolve: {
            unitType: UnitTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-type/new',
        component: UnitTypeUpdateComponent,
        resolve: {
            unitType: UnitTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-type/:id/edit',
        component: UnitTypeUpdateComponent,
        resolve: {
            unitType: UnitTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unitTypePopupRoute: Routes = [
    {
        path: 'unit-type/:id/delete',
        component: UnitTypeDeletePopupComponent,
        resolve: {
            unitType: UnitTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
