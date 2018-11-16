import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitTypeItem } from 'app/shared/model/unit-type-item.model';
import { UnitTypeItemService } from './unit-type-item.service';

@Component({
    selector: 'jhi-unit-type-item-delete-dialog',
    templateUrl: './unit-type-item-delete-dialog.component.html'
})
export class UnitTypeItemDeleteDialogComponent {
    unitTypeItem: IUnitTypeItem;

    constructor(
        private unitTypeItemService: UnitTypeItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.unitTypeItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'unitTypeItemListModification',
                content: 'Deleted an unitTypeItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-unit-type-item-delete-popup',
    template: ''
})
export class UnitTypeItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unitTypeItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UnitTypeItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.unitTypeItem = unitTypeItem;
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
