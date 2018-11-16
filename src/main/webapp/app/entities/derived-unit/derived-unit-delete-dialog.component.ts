import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDerivedUnit } from 'app/shared/model/derived-unit.model';
import { DerivedUnitService } from './derived-unit.service';

@Component({
    selector: 'jhi-derived-unit-delete-dialog',
    templateUrl: './derived-unit-delete-dialog.component.html'
})
export class DerivedUnitDeleteDialogComponent {
    derivedUnit: IDerivedUnit;

    constructor(
        private derivedUnitService: DerivedUnitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.derivedUnitService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'derivedUnitListModification',
                content: 'Deleted an derivedUnit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-derived-unit-delete-popup',
    template: ''
})
export class DerivedUnitDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ derivedUnit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DerivedUnitDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.derivedUnit = derivedUnit;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
