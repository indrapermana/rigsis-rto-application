import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DerivedUnit } from 'app/shared/model/derived-unit.model';
import { DerivedUnitService } from './derived-unit.service';
import { DerivedUnitComponent } from './derived-unit.component';
import { DerivedUnitDetailComponent } from './derived-unit-detail.component';
import { DerivedUnitUpdateComponent } from './derived-unit-update.component';
import { DerivedUnitDeletePopupComponent } from './derived-unit-delete-dialog.component';
import { IDerivedUnit } from 'app/shared/model/derived-unit.model';

@Injectable({ providedIn: 'root' })
export class DerivedUnitResolve implements Resolve<IDerivedUnit> {
    constructor(private service: DerivedUnitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((derivedUnit: HttpResponse<DerivedUnit>) => derivedUnit.body));
        }
        return of(new DerivedUnit());
    }
}

export const derivedUnitRoute: Routes = [
    {
        path: 'derived-unit',
        component: DerivedUnitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DerivedUnits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'derived-unit/:id/view',
        component: DerivedUnitDetailComponent,
        resolve: {
            derivedUnit: DerivedUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DerivedUnits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'derived-unit/new',
        component: DerivedUnitUpdateComponent,
        resolve: {
            derivedUnit: DerivedUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DerivedUnits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'derived-unit/:id/edit',
        component: DerivedUnitUpdateComponent,
        resolve: {
            derivedUnit: DerivedUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DerivedUnits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const derivedUnitPopupRoute: Routes = [
    {
        path: 'derived-unit/:id/delete',
        component: DerivedUnitDeletePopupComponent,
        resolve: {
            derivedUnit: DerivedUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DerivedUnits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
