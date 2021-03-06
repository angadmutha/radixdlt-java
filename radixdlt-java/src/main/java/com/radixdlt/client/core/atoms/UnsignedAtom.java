package com.radixdlt.client.core.atoms;

import com.radixdlt.client.core.address.EUID;
import com.radixdlt.client.core.crypto.ECSignature;
import java.util.Collections;

public class UnsignedAtom {
	private final Atom atom;
	public UnsignedAtom(Atom atom) {
		this.atom = atom;
	}

	public Atom getRawAtom() {
		return atom;
	}

	public RadixHash getHash() {
		return atom.getHash();
	}

	public Atom sign(ECSignature signature, EUID signatureId) {
		// TODO: Remove need to create a new object
		if (atom instanceof TransactionAtom) {
			TransactionAtom unsigned = (TransactionAtom)atom;
			return new TransactionAtom(unsigned.getParticles(), unsigned.getDestinations(), unsigned.getPayload(), unsigned.getEncryptor(), signatureId, signature, atom.getTimestamp());
		} else if (atom instanceof ApplicationPayloadAtom){
			ApplicationPayloadAtom unsigned = (ApplicationPayloadAtom) atom;
			return new ApplicationPayloadAtom(unsigned.getApplicationId(), unsigned.getParticles(), unsigned.getDestinations(), unsigned.getPayload(), unsigned.getEncryptor(), atom.getTimestamp(), signatureId, signature);
		} else {
			throw new IllegalStateException("Cannot create signed atom");
		}
	}
}
