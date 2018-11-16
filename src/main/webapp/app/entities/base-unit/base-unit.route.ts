import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseUnit } from 'app/shared/model/base-unit.model';
import { BaseUnitService } from './base-unit.service';
import { BaseUnitComponent } from './base-unit.component';
import { BaseUnitDetailComponent } from './base-unit-detail.component';
import { BaseUnitUpdateComponent } from './base-unit-update.component';
import { BaseUnitDeletePopupComponent } from './base-unit-delete-dialog.component';
import { IBaseUnit } from 'app/shared/model/base-unit.model';

@Injectable({ providedIn: 'root' })
export class BaseUnitResolve implements Resolve<IBaseUnit> {
    constructor(private service: BaseUnitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((baseUnit: HttpResponse<BaseUnit>) => baseUnit.body));
        }
        return of(new BaseUnit());
    }
}

export const baseUnitRoute: Routes = [
    {
        path: 'base-unit',
        component: BaseUnitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BaseUnits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'base-unit/:id/view',
        component: BaseUnitDetailComponent,
        resolve: {
            baseUnit: BaseUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BaseUnits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'base-unit/new',
        component: BaseUnitUpdateComponent,
        resolve: {
            baseUnit: BaseUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BaseUnits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'base-unit/:id/edit',
        component: BaseUnitUpdateComponent,
        resolve: {
            baseUnit: BaseUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BaseUnits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const baseUnitPopupRoute: Routes = [
    {
        path: 'base-unit/:id/delete',
        component: BaseUnitDeletePopupComponent,
        resolve: {
            baseUnit: BaseUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BaseUnits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
