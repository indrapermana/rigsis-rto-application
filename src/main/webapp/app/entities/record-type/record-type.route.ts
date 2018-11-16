import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RecordType } from 'app/shared/model/record-type.model';
import { RecordTypeService } from './record-type.service';
import { RecordTypeComponent } from './record-type.component';
import { RecordTypeDetailComponent } from './record-type-detail.component';
import { RecordTypeUpdateComponent } from './record-type-update.component';
import { RecordTypeDeletePopupComponent } from './record-type-delete-dialog.component';
import { IRecordType } from 'app/shared/model/record-type.model';

@Injectable({ providedIn: 'root' })
export class RecordTypeResolve implements Resolve<IRecordType> {
    constructor(private service: RecordTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((recordType: HttpResponse<RecordType>) => recordType.body));
        }
        return of(new RecordType());
    }
}

export const recordTypeRoute: Routes = [
    {
        path: 'record-type',
        component: RecordTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-type/:id/view',
        component: RecordTypeDetailComponent,
        resolve: {
            recordType: RecordTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-type/new',
        component: RecordTypeUpdateComponent,
        resolve: {
            recordType: RecordTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-type/:id/edit',
        component: RecordTypeUpdateComponent,
        resolve: {
            recordType: RecordTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recordTypePopupRoute: Routes = [
    {
        path: 'record-type/:id/delete',
        component: RecordTypeDeletePopupComponent,
        resolve: {
            recordType: RecordTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
